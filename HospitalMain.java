class Patient {
    protected String name;
    protected int age;
    protected String disease;
    protected String admissionDate;

    Patient(String name, int age, String disease, String admissionDate) {
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.admissionDate = admissionDate;
    }

    // senior citizens (60+) get 10% discount
    double getDiscount() {
        if (age >= 60) {
            return 0.10;
        }
        return 0;
    }

    double generateBill() {
        return 500; // basic registration fee
    }
}

class InPatient extends Patient {
    private int days;
    private double dailyCharge;

    InPatient(String name, int age, String disease, String admissionDate, int days, double dailyCharge) {
        super(name, age, disease, admissionDate);
        this.days = days;
        this.dailyCharge = dailyCharge;
    }

    @Override
    double generateBill() {
        double total = days * dailyCharge;
        total = total - (total * getDiscount());
        return total;
    }
}

class OutPatient extends Patient {
    private int visits;
    private double visitFee;

    OutPatient(String name, int age, String disease, String admissionDate, int visits, double visitFee) {
        super(name, age, disease, admissionDate);
        this.visits = visits;
        this.visitFee = visitFee;
    }

    @Override
    double generateBill() {
        double total = visits * visitFee;
        total = total - (total * getDiscount());
        return total;
    }
}

class Appointment {
    private String date;
    private Patient patient;
    private String[] followUps = new String[5];
    private int followUpCount = 0;

    Appointment(String date, Patient patient) {
        this.date = date;
        this.patient = patient;
    }

    Patient getPatient() {
        return patient;
    }

    void addFollowUp(String date) {
        if (followUpCount < 5) {
            followUps[followUpCount] = date;
            followUpCount++;
        } else {
            System.out.println("Follow-up list is full.");
        }
    }

    void display() {
        System.out.println("  Appointment date: " + date);
        System.out.println("  Patient: " + patient.name);
        for (int i = 0; i < followUpCount; i++) {
            System.out.println("  Follow-up " + (i + 1) + ": " + followUps[i]);
        }
    }
}

class Doctor {
    private String name;
    private String specialization;
    private double consultationFee;
    private Appointment[] appointments = new Appointment[10];
    private int appointmentCount = 0;

    Doctor(String name, String specialization, double consultationFee) {
        this.name = name;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    String getName() {
        return name;
    }

    String getSpecialization() {
        return specialization;
    }

    Appointment addAppointment(String date, Patient p) {
        if (appointmentCount < 10) {
            Appointment a = new Appointment(date, p);
            appointments[appointmentCount] = a;
            appointmentCount++;
            return a;
        }
        System.out.println("Dr. " + name + " has no free slots.");
        return null;
    }

    void display() {
        System.out.println("Dr. " + name + " (" + specialization + ") Fee: " + consultationFee);
        for (int i = 0; i < appointmentCount; i++) {
            appointments[i].display();
        }
    }
}

class Hospital {
    private Patient[] patients = new Patient[20];
    private Doctor[] doctors = new Doctor[10];
    private int patientCount = 0;
    private int doctorCount = 0;

    void addPatient(Patient p) {
        patients[patientCount] = p;
        patientCount++;
    }

    void addDoctor(Doctor d) {
        doctors[doctorCount] = d;
        doctorCount++;
    }

    // schedule using a Doctor object
    void schedule(Patient p, Doctor d, String date) {
        d.addAppointment(date, p);
        System.out.println(p.name + " scheduled with Dr. " + d.getName() + " on " + date);
    }

    // schedule using doctor's name (overloaded)
    void schedule(Patient p, String doctorName, String date) {
        for (int i = 0; i < doctorCount; i++) {
            if (doctors[i].getName().equals(doctorName)) {
                schedule(p, doctors[i], date);
                return;
            }
        }
        System.out.println("Doctor " + doctorName + " not found.");
    }

    void showDoctors() {
        for (int i = 0; i < doctorCount; i++) {
            doctors[i].display();
        }
    }

    void showBills() {
        for (int i = 0; i < patientCount; i++) {
            System.out.println(patients[i].name + " bill: " + patients[i].generateBill());
        }
    }
}

public class HospitalMain {
    public static void main(String[] args) {
        Hospital h = new Hospital();

        Doctor d1 = new Doctor("Ahmed", "Cardiology", 2000);
        Doctor d2 = new Doctor("Sara", "Dentist", 1500);
        h.addDoctor(d1);
        h.addDoctor(d2);

        InPatient p1 = new InPatient("Ali", 65, "Heart Disease", "01-10-2026", 5, 3000);
        OutPatient p2 = new OutPatient("Hina", 25, "Toothache", "02-10-2026", 3, 800);
        h.addPatient(p1);
        h.addPatient(p2);

        h.schedule(p1, d1, "05-10-2026");
        h.schedule(p2, "Sara", "06-10-2026");
        h.schedule(p2, "Zain", "07-10-2026");

        Appointment a = d2.addAppointment("10-10-2026", p2);
        a.addFollowUp("17-10-2026");

        System.out.println();
        h.showDoctors();
        System.out.println();
        h.showBills();
    }
}
