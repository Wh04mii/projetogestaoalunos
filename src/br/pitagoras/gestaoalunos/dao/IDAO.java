package br.pitagoras.gestaoalunos.dao;

import java.util.List;

public interface IDAO<T> {

    public T pesquisar(int id);

    public List<T> pesquisar();

    public boolean inserir(T model);

    public boolean alterar(T model);

    public boolean deletar(T model);
}
