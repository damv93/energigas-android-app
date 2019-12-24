package pe.focusit.energigas.data.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pe.focusit.energigas.data.net.dto.ClientDto;
import pe.focusit.energigas.data.net.dto.ExpenseTypeDto;
import pe.focusit.energigas.data.net.dto.GasStationDto;
import pe.focusit.energigas.data.net.dto.SupplierDto;
import pe.focusit.energigas.data.net.dto.SupplierxExpenseTypeDto;

public class SyncResponse extends ApiBaseResponse {

    @SerializedName("tipo_gastos")
    private List<ExpenseTypeDto> expenseTypes;
    @SerializedName("clientes")
    private List<ClientDto> clients;
    @SerializedName("proveedores")
    private List<SupplierDto> suppliers;
    @SerializedName("prov_tipo_gasto")
    private List<SupplierxExpenseTypeDto> supplierxExpenseTypes;
    @SerializedName("grifos")
    private List<GasStationDto> gasStations;

    public List<ExpenseTypeDto> getExpenseTypes() {
        return expenseTypes;
    }

    public void setExpenseTypes(List<ExpenseTypeDto> expenseTypes) {
        this.expenseTypes = expenseTypes;
    }

    public List<ClientDto> getClients() {
        return clients;
    }

    public void setClients(List<ClientDto> clients) {
        this.clients = clients;
    }

    public List<SupplierDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierDto> suppliers) {
        this.suppliers = suppliers;
    }

    public List<SupplierxExpenseTypeDto> getSupplierxExpenseTypes() {
        return supplierxExpenseTypes;
    }

    public void setSupplierxExpenseTypes(List<SupplierxExpenseTypeDto> supplierxExpenseTypes) {
        this.supplierxExpenseTypes = supplierxExpenseTypes;
    }

    public List<GasStationDto> getGasStations() {
        return gasStations;
    }

    public void setGasStations(List<GasStationDto> gasStations) {
        this.gasStations = gasStations;
    }
}
