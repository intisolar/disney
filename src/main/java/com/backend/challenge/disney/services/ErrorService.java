/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.challenge.disney.services;

public class ErrorService extends Exception {

    public ErrorService(String msg) {
        super(msg);
    }
}
