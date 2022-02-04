package eu.ensup.my_resto.model;

/**
 * The enum Status.
 */
public enum Status {
    /**
     * The En attente.
     */
    EN_ATTENTE("En attente"),
    /**
     * The En cours.
     */
    EN_COURS("En cours"),
    /**
     * Terminee status.
     */
    TERMINEE("Terminée"),
    /**
     * Annulee status.
     */
    ANNULEE("Annulée");

    private String stat;

    Status(String status) {
        this.stat = status;
    }
}
