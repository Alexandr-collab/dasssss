package com.example.das;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "deals")
public class DealsListWrapper {

    private List<Orders> deals;

    @XmlElement(name = "deal")
    public List<Orders> getDeals() {
        return deals;
    }

    public void setDeals(List<Orders> deals){
        this.deals = deals;
    }
}

