package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    private String orderCode;
    private String customerId;
    private String menuId;
    private int numOfTables;
    private Date eventDate;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.customerId);
        hash = 97 * hash + Objects.hashCode(this.menuId);
        hash = 97 * hash + Objects.hashCode(this.eventDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.customerId, other.customerId)) {
            return false;
        }
        if (!Objects.equals(this.menuId, other.menuId)) {
            return false;
        }
        return Objects.equals(this.eventDate, other.eventDate);
    }
    
    private String generateOrderCode() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(now);
    }
    
    public Order() {
        this.orderCode = generateOrderCode();
        this.customerId = "";
        this.menuId = "";
        this.eventDate = new Date();
    }

    public Order(String customerId, String menuId, int numOfTables, Date eventDate) {
        this.orderCode = generateOrderCode();
        this.customerId = customerId;
        this.menuId = menuId;
        this.numOfTables = numOfTables;
        this.eventDate = eventDate;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getMenuId() {
        return menuId;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "Order{" + "orderCode=" + orderCode + ", customerId=" + customerId + ", menuId=" + menuId + ", numOfTables=" + numOfTables + ", eventDate=" + eventDate + "}";
    }
    
    
}
