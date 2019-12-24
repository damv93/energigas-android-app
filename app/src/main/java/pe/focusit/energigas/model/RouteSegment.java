package pe.focusit.energigas.model;

public class RouteSegment {

    private long id;
    private long routeId;
    private int stopNumber;
    private String stopPlace;
    private long clientId;
    private String startPlace;
    private int progressPercentage;
    private double expenseAmount;
    private Client client;
    private Integer requestedAmount;
    private ProductDispatch productDispatch;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public String getStopPlace() {
        return stopPlace;
    }

    public void setStopPlace(String stopPlace) {
        this.stopPlace = stopPlace;
    }

    public long getClientId() {
        return clientId;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getSegmentDescription() {
        if (startPlace == null && stopPlace == null)
            return "";
        return (startPlace == null ? "" : startPlace) + " - " + (stopPlace == null ? "" : stopPlace);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.clientId = (client == null) ? 0 : client.getId();
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public ProductDispatch getProductDispatch() {
        return productDispatch;
    }

    public void setProductDispatch(ProductDispatch productDispatch) {
        this.productDispatch = productDispatch;
    }
}
