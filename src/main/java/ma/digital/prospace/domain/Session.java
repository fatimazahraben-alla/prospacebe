package ma.digital.prospace.domain;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "Session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "transaction_id", length = 50, nullable = false)
    private Long TransactionId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Date CreatedAt;



    @NotNull
    @Size(max = 500)
    @Column(name = "json_data", length = 500, nullable = false)
    private String JsonData;


    @ManyToOne
    @JoinColumn(name = "association", nullable = false)
    private Association association;

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Long getTransactionId() {
  return TransactionId;
 }

 public void setTransactionId(Long transactionId) {
  TransactionId = transactionId;
 }

 public Date getCreatedAt() {
  return CreatedAt;
 }

 public void setCreatedAt(Date createdAt) {
  CreatedAt = createdAt;
 }

 public String getJsonData() {
  return JsonData;
 }

 public void setJsonData(String jsonData) {
  JsonData = jsonData;
 }

 public Association getAssociation() {
  return association;
 }

 public void setAssociation(Association association) {
  this.association = association;
 }
}
