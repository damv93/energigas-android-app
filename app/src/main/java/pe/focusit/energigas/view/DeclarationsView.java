package pe.focusit.energigas.view;

import java.util.List;

import pe.focusit.energigas.model.Declaration;

public interface DeclarationsView extends BaseView {
    void onGetDeclarationsSuccess(List<Declaration> pending, List<Declaration> observed, List<Declaration> approved);
    void onGetDeclarationsError(String errorMessage);
}
