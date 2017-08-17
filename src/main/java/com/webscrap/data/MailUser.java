package com.webscrap.data;

public class MailUser
{
    private String firstName;
    private String lastName;
    private String emailAdress;

    public MailUser(){}

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getEmailAdress()
    {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress)
    {
        this.emailAdress = emailAdress;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
