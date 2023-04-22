package com.example.photoforyou;

class LogEntry {
    String date;
    String table_name;
    String type;
    Detail detail;

    // Méthode pour récupérer la valeur de date
    public String getDate() {
        return this.date;
    }
    // Méthode pour récupérer la valeur de table_name
    public String getTable_name() {
        return this.table_name;
    }
    // Méthode pour récupérer la valeur de type
    public String getType() {
        if ("A".equals(this.type)) {
            return "Ajouter";
        } else if ("D".equals(this.type)) {
            return "Supprimer";
        } else if ("M".equals(this.type)) {
            return "Modifier";
        } else {
            return null; // ou une valeur par défaut si nécessaire
        }
    }
    // Méthode pour récupérer la valeur de detail
    public String getDetail() {
        return detail.toString();
    }


        static class Detail {
        Integer idPhoto;
        Integer idPhotographe;
        Integer idUser;
        Integer rankUser;
        String reason;
        Integer idTag;

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                if (idPhoto != null) {
                    sb.append("idPhoto=").append(idPhoto).append("\n");
                }
                if (idPhotographe != null) {
                    sb.append("idPhotographe=").append(idPhotographe).append("\n");
                }
                if (idUser != null) {
                    sb.append("idUser=").append(idUser).append("\n");
                }
                if (rankUser != null) {
                    sb.append("rankUser=").append(rankUser).append("\n");
                }
                if (reason != null) {
                    sb.append("reason='").append(reason).append("\n");
                }
                if (idTag != null) {
                    sb.append("idTag=").append(idTag).append("\n");
                }
                // supprime le dernier retour à la ligne
                if (sb.lastIndexOf("\n") == sb.length() - 2) {
                    sb.delete(sb.length() - 2, sb.length());
                }
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 1);
                }
                return sb.toString();
            }

    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "date='" + date + '\'' +
                ", table_name='" + table_name + '\'' +
                ", type='" + type + '\'' +
                ", detail=" + detail.toString() +
                '}';
    }
}
