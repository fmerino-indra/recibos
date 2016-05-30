package es.cnc.suscripciones.domain;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "direct_debit_order")
public class DirectDebitOrder extends AbstractEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	public DirectDebitOrder() {
    	super(DirectDebitOrder.class);
	}
    @Column(name = "msgId", length = 35)
    private String msgId;
    
    @Column(name = "creDtTm")
    private LocalDateTime creDtTm;
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public LocalDateTime getCreDtTm() {
		return creDtTm;
	}

	public void setCreDtTm(LocalDateTime creDtTm) {
		this.creDtTm = creDtTm;
	}
}
