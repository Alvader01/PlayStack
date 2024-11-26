package com.github.alvader01.Tests;

import com.github.alvader01.Model.Connection.ConnectionProperties;
import com.github.alvader01.Utils.XMLManager;

public class testconnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
