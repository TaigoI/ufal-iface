package br.ufal.ic.prog2;

public enum TextColors {
    RED("\033[0;32m");

    private final String colorCode;

    TextColors(String colorCode){
        this.colorCode = colorCode;
    }
}
