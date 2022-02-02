package eu.ensup.my_resto.model;

public enum Status {
    EN_ATTENTE("En attente"),
    EN_COURS("En cours"),
    TERMINEE("Terminée"),
    ANNULEE("Annulée");

    private String stat;

    Status(String status) {
        this.stat = status;
    }
}
