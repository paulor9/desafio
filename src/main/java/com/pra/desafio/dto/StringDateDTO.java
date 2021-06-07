package com.pra.desafio.dto;

import com.pra.desafio.exception.ValidateException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringDateDTO implements Serializable {

    private String dateStr;
    private Date  date;
    private final DateFormat  df = new SimpleDateFormat("dd/MM/yyyy");

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr( String dateStr ) {
        this.dateStr = dateStr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public StringDateDTO ( String dtStr) throws ValidateException{
        try {
            df.setLenient(false);
            df.parse(dtStr);
            date = df.parse(dtStr);
            dateStr = dtStr;
        } catch (ParseException e) {
            throw new ValidateException(String.format("Date %s is invalid",  dtStr ) );
        }

    }








}
