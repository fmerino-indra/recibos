package es.cnc.jasper.data;

import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SuscriptorBeanCollectionDataSourceSample extends JRBeanCollectionDataSource {

	public SuscriptorBeanCollectionDataSourceSample() {
		this(SuscriptorCollectionHelper.obtain());
	}
	public SuscriptorBeanCollectionDataSourceSample(Collection<?> beanCollection) {
		super(beanCollection);
	}

}
