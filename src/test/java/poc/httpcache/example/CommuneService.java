package poc.httpcache.example;

import java.util.List;

public record CommuneService() {
    private static final Commune LA_TESTE = new Commune("33529", "La Teste de Buch");
    private static final Commune BERCK = new Commune("62108", "Berck sur mer");

    public List<Commune> getAllCommunes() {
        return List.of(LA_TESTE, BERCK);
    }
}
