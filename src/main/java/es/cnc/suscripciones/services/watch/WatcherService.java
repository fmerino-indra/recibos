package es.cnc.suscripciones.services.watch;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.MessageFormat;

//@Service
public class WatcherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherService.class);

    private final WatchService watchService;
    private Map<WatchKey, PathMonitor> keys;
    private Path pendingDirectoryPath = null;
    private Path processedDirectoryPath = null;
    private Path errorDirectoryPath = null;
    
    @Value("${app.xml.file.watch.pending.dir}")
    private String pendingDir;

    @Value("${app.xml.file.watch.processed.dir}")
    private String processedDir;
    
    @Value("${app.xml.file.watch.error.dir}")
    private String errorDir;
    
    public WatcherService() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        
    }

    @PostConstruct
    public void init() {
    	String message = null;
        pendingDirectoryPath = FileSystems.getDefault().getPath(pendingDir);
    	if (!Files.exists(pendingDirectoryPath) ) {
    		message = MessageFormat.format("[WatcherService] Directory for pending files:{0} does not exist", pendingDir);
    		LOGGER.error(message);
    		throw new RuntimeException(message);
    	}
    	
        processedDirectoryPath = FileSystems.getDefault().getPath(processedDir);
    	if (!Files.exists(processedDirectoryPath) ) {
    		message = MessageFormat.format("[WatcherService] Directory for processed files:{0} does not exist", processedDir);
    		LOGGER.error(message);
    		throw new RuntimeException(message);
    	}
    	
        errorDirectoryPath = FileSystems.getDefault().getPath(errorDir);
    	if (!Files.exists(errorDirectoryPath) ) {
    		message = MessageFormat.format("[WatcherService] Directory for error files:{0} does not exist", errorDir);
    		LOGGER.error(message);
    		throw new RuntimeException(message);
    	}
    	
    	try {
			pendingDirectoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
		} catch (IOException e) {
    		message = MessageFormat.format("[WatcherService] An error has been catched while watching directory {0}", pendingDir);
    		LOGGER.error(message);
			throw new RuntimeException(message,e);
		}
    	processEvents();
    }
    
    private void processEvents() {
    	WatchKey key = null;
    	List<WatchEvent<?>> events = null;
    	Path path = null;
    	Kind<?> kindOfEvent = null;
    	while (true) {
    		LOGGER.info("[WatcherService] Waiting for a change on {}", pendingDir);
    		try {
				key = watchService.take();
				if (key.isValid()) {
					events = key.pollEvents();
					for (WatchEvent<?> event:events) {
						path = (Path)event.context();
						kindOfEvent = event.kind();
			    		LOGGER.info("[WatcherService] An event has been received ({})",kindOfEvent);
// TODO [FMM] Algo que hacer
			    		if (kindOfEvent.equals(StandardWatchEventKinds.ENTRY_CREATE))
			    			moveToProcessed(path);
					}
				}
				key.reset();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private void moveToProcessed(Path file) {
    	Path target = null;
    	target = processedDirectoryPath.resolve(file.getFileName());
    	Path source = null;
    	source = pendingDirectoryPath.resolve(file.getFileName());
    	
    	try {
			Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
