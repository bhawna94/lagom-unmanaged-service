package edu.knoldus.external.impl.Repository;

public class Operation {

    public static String addUser(){

        return "insert into user.external (user_id, id, title, body) values(?,?,?,?)";

    }
}
