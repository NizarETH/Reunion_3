package com.openclassrooms.reunion.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Model object representing a Reunion
 */
public class Reunion {

    /** Identifier */
    private long id;

    /** Full name */
    private String nameReunion;

    /** Heure */
    private String heureReunion;

    /** Date */
    private String dateReunion;

    /** Nom De La Salle */
    private String nameSalleReunion;

    /** mail address participant*/
    private List<String> mailAddresse;

    /**
     * Constructor
     * @param id
     * @param nameReunion
     * @param heureReunion
     * @param salleReunion
     * @param addEmail
     */
    public Reunion(long id, String nameReunion, String heureReunion,String dateReunion,String salleReunion,
                   List<String> addEmail) {
        this.id = id;
        this.nameReunion = nameReunion;
        this.heureReunion = heureReunion;
        this.dateReunion = dateReunion;
        this.nameSalleReunion = salleReunion;
        this.mailAddresse = addEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameReunion() {
        return nameReunion;
    }

    public void setNameReunion(String name) {
        this.nameReunion = nameReunion;
    }

    public String getHeureReunion() {
        return heureReunion;
    }

    public void setHeureReunion(String heureReunion) {
        this.heureReunion = heureReunion;
    }

    public String getDateReunion() { return dateReunion;    }

    public void setDateReunion(String dateReunionReunion) {this.dateReunion = dateReunion;    }

    public String getNameSalleReunion() {
        return nameSalleReunion;
    }

    public void setNameSalleReunion(String nameSalleReunion) {
        this.nameSalleReunion = nameSalleReunion;
    }

    public List<String> getMailAddresse() {
        return mailAddresse ;
    }

    public void setMailAddresse(List<String> addEmail) {
        this.mailAddresse = addEmail;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion reunion = (Reunion) o;
        return Objects.equals(id, reunion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void remove(Reunion reunion) {
    }

    public void add(Reunion reunion) {
    }

 public static Comparator<Reunion> ReunionDateAscComparator = new Comparator<Reunion>() {
     @Override
     public int compare(Reunion reunion, Reunion r1) {
         String reunionHD=reunion.getDateReunion()+reunion.getHeureReunion();
         String r1HD=r1.getDateReunion()+r1.getHeureReunion();
         return (reunionHD.compareTo(r1HD));

     }
 };
  public static Comparator<Reunion> ReunionDateDesComparator = new Comparator<Reunion>() {
         @Override
         public int compare(Reunion reunion, Reunion r1) {
             String reunionHD=reunion.getDateReunion()+reunion.getHeureReunion();
             String r1HD=r1.getDateReunion()+r1.getHeureReunion();
             return r1HD.compareTo(reunionHD);
         }
 };
    public static Comparator<Reunion> ReunionSalleComparator = new Comparator<Reunion>() {
        @Override
        public int compare(Reunion reunion, Reunion r1) {
            return r1.getNameSalleReunion().compareTo(reunion.getNameSalleReunion());
        }
    };

}
