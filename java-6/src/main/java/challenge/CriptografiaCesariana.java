package challenge;

public class CriptografiaCesariana implements Criptografia {


    @Override
    public String criptografar(String texto) {
        validaText(texto);
        return algoritmodeCesar(texto, 3);
    }

    @Override
    public String descriptografar(String texto) {
        validaText(texto);
        return algoritmodeCesar(texto, -3);
    }

    private String algoritmodeCesar(String texto, int step) {
        String alfabeto = "abcdefghijklmnopqrstuvwxyz";
        String ans = "";
        int tam = texto.length();
        int tamAlfa = alfabeto.length();

        texto = texto.toLowerCase();

        for (int i = 0; i < tam; i++) {
            char c = texto.charAt(i);
            int idx = alfabeto.indexOf(c);
            if (idx == -1) {
                ans += c;
            } else {
                ans  += alfabeto.charAt((idx+step)%tamAlfa);
            }
        }

        return ans;
    }

    private void validaText (String texto) {
        if (texto == null) {
            throw new NullPointerException();
        } else if (texto.trim().equals("")) {
            throw new IllegalArgumentException();
        }
    }

}