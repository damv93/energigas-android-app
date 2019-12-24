package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "DEPOSIT")
public class DepositEntity {
    @Id(autoincrement = true)
    private Long id;
    private Long routeId;
    private Double amount;
    private String operationNumber;
    private Date date;
    private String concept;
    @Generated(hash = 92697615)
    public DepositEntity(Long id, Long routeId, Double amount,
            String operationNumber, Date date, String concept) {
        this.id = id;
        this.routeId = routeId;
        this.amount = amount;
        this.operationNumber = operationNumber;
        this.date = date;
        this.concept = concept;
    }
    @Generated(hash = 635875041)
    public DepositEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    public Double getAmount() {
        return this.amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getOperationNumber() {
        return this.operationNumber;
    }
    public void setOperationNumber(String operationNumber) {
        this.operationNumber = operationNumber;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getConcept() {
        return this.concept;
    }
    public void setConcept(String concept) {
        this.concept = concept;
    }
}
