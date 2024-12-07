package users;
public class User { // done.
    private String surname;
    private String forename;
    private String securityQuestion = "";
    private String securityAnswer = "";
    private String dateOfBirth;

    public User(String forename, String surname, String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
        this.forename = forename;
        this.surname = surname;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }






}
