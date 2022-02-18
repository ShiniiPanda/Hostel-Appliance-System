public abstract class Constants {

    // THIS CLASS IS PURELY USED AS A PLACEHOLDER FOR CONSTANT VALUES THAT DO NOT CHANGE OVER THE COURSE
    // OF THE ENTIRE PROGRAM, MOST NOTABLY INCLUDES FILE PATHS AND COMBO BOX CHOICES FOR STANDARDIZATION PURPOSES.

    //TEXT FILE PATHS START
    public static final String MAIN_TEXTFILE_DIRECTORY = "./TextFiles";
    public static final String APPLIANCE_FILE = "./TextFiles/Appliances.txt";
    public static final String APPOINTMENT_FILE = "./TextFiles/Appointments.txt";
    public static final String LOG_FILE = "./TextFiles/AuditLogs.txt";
    public static final String CUSTOMER_FILE = "./TextFiles/Customers.txt";
    public static final String EMPLOYEE_FILE = "./TextFiles/Employees.txt";
    public static final String FEEDBACK_FILE = "./TextFiles/Feedback.txt";
    public static final String TRANSACTION_FILE = "./TextFiles/Transactions.txt";
    //TEXT FILE PATHS END

    //JFrame Constant Title For All Pages
    public static final String PAGE_TITLE = "APU Hostel Service Centre";

    public static final String [] years = {"1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969",
            "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979",
            "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989",
            "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999",
            "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
            "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019",
            "2020", "2021", "2022"};

    public static final String [] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    public static final String [] days31 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public static final String [] days30 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};

    public static final String [] days29 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "29"};

    public static final String [] days28 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};

    public static final String [] months31 = {"01", "03", "05", "07", "08", "10", "12"};

    //Regular Expression used for password check, used exclusively for End-User registration.
    public static final String passwordRegEx = "^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$";

}
