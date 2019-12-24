package pe.focusit.energigas.view;

public interface SyncView extends BaseView {
    void onSyncSuccess();
    void onSyncError(String errorMessage);
}
