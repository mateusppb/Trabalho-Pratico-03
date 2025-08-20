import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class Show {
    private String show_id, type, title, rating, duration, director, country, listed_in;
    private String[] cast;
    private int release_year;
    private Date date_added;

    public Show() {
        this.show_id = this.type = this.title = this.rating = this.duration = this.director = this.country = "NaN";
        this.cast = new String[]{"NaN"};
        this.date_added = null;
        this.release_year = 0;
    }

    //metodos setters e getters de todos os atributos
    public String getId() {return this.show_id; }
    public void setId(String show_id) {this.show_id = show_id.isEmpty() ? "NaN" : show_id; }

    public String getType() {return this.type; }
    public void setType(String type) {this.type = type.isEmpty() ? "NaN" : type; }

    public String getTitle() {return this.title; }
    public void setTitle(String title) {
        title = title.replace("\"", "");
        this.title = title.isEmpty() ? "NaN" : title;
    }

    public String getDirector() {return director; }
    public void setDirector(String director) {this.director = director.isEmpty() ? "NaN" : director;}

    public String[] get_cast() {return cast; }
    public void set_cast(String[] cast){
        if(cast == null || cast.length == 0) {
            this.cast = new String[]{"NaN"};
        }
        else {
            for(int i = 0; i < cast.length - 1; i++) {
                int menor = i;
                for(int j = i + 1; j < cast.length; j++) {
                    if (cast[j].compareTo(cast[menor]) < 0) {
                        menor = j;
                    }
                }
                String temp = cast[i];
                cast[i] = cast[menor];
                cast[menor] = temp;
            }
            this.cast = cast;
        }
    }

    public String get_country() {return country; }
    public void set_country(String country) {this.country = country.isEmpty() ? "NaN" : country; }

    public Date getDate_added() {return date_added; }
    public void setDate_added(Date date_added) {this.date_added = date_added; }

    public int getRelease_year() {return release_year; }
    public void setRelease_year(int release_year) {this.release_year = (release_year == 0) ? 0 : release_year; }

    public String getRating() {return rating; }
    public void setRating(String rating) {this.rating = rating.isEmpty() ? "NaN" : rating; }

    public String getDuration() {return duration; }
    public void setDuration(String duration) {this.duration = duration.isEmpty() ? "NaN" : duration; }

    public String getListed_in() {return listed_in; }
    public void setListed_in(String listed_in) {this.listed_in = listed_in.isEmpty() ? "NaN" : listed_in; }


    public void ler(String linha) throws Exception {
        // Lista para guardar os campos separados
        List<String> atributos = new ArrayList<>();
        StringBuilder campoAtual = new StringBuilder();
        boolean Aspas = false;
    
        // Separação manual dos campos
        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
    
            if (c == '"') {
                // Trata aspas duplas escapadas ("")
                if (Aspas && i + 1 < linha.length() && linha.charAt(i + 1) == '"') {
                    campoAtual.append('"');
                    i++;
                } else {
                    Aspas = !Aspas;
                }
            } else if (c == ',' && !Aspas) {
                // Fim de campo
                atributos.add(campoAtual.toString());
                campoAtual.setLength(0); // esvazia o campo pro próximo atributo
            } else {
                campoAtual.append(c);
            }
        }
        atributos.add(campoAtual.toString());
    
        // Preenchendo atributos
        setId(atributos.get(0));
        setType(atributos.get(1));
        setTitle(atributos.get(2));
        setDirector(atributos.get(3));
    
        String arrayCast = atributos.get(4);
        if (arrayCast.isEmpty()) {
            set_cast(new String[0]);
        } else {
            set_cast(arrayCast.split(",\\s*"));
        }
    
        set_country(atributos.get(5));
    
        String dataStr = atributos.get(6);
        if (dataStr.isEmpty()) {
            // March 1, 1900 caso esteja vazia
            this.date_added = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("March 1, 1900");
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                this.date_added = sdf.parse(dataStr);
            } catch (Exception e) {
                this.date_added = null; // se não conseguir converter
            }
        }

        if (!atributos.get(7).isEmpty()) {
            this.release_year = Integer.parseInt(atributos.get(7));
        }
        this.rating = atributos.get(8);
        this.duration = atributos.get(9);
        this.listed_in = atributos.get(10);
    }


        public void imprimir() {

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            System.out.println("=> "+this.show_id + " ## " +
                               this.title + " ## " +
                               this.type + " ## " +
                               this.director + " ## [" +
                               String.join(", ", this.cast) + "] ## " +
                               this.country + " ## " +
                               (this.date_added != null ? sdf.format(this.date_added) : "NaN") + " ## " +
                               this.release_year + " ## " +
                               this.rating + " ## " +
                               this.duration + " ## [" +
                               this.listed_in+"] ##");
        }

    }

class No {
    public Show elemento;
    public No esq, dir;

    public No(Show s) {
        this.elemento = s;
        this.esq = null;
        this.dir = null;
    }
}

class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    public void inserir(Show s) throws Exception {
        raiz = inserir(s, raiz);
    }

    private No inserir(Show s, No i) throws Exception {
        if (i == null) {
            i = new No(s);
        } else {
            int cmp = s.getTitle().compareTo(i.elemento.getTitle());
            if (cmp < 0) {
                i.esq = inserir(s, i.esq);
            } else if (cmp > 0) {
                i.dir = inserir(s, i.dir);
            } else {
                // Já existe, não insere
                throw new Exception("Erro: elemento duplicado");
            }
        }
        return i;
    }

    public void pesquisar(String title) {
        System.out.print("=>raiz  ");
        boolean encontrado = pesquisar(title, raiz);
        if (encontrado) {
            System.out.println("SIM");
        } else {
            System.out.println("NAO");
        }
    }

    private boolean pesquisar(String title, No i) {
        boolean resp;
        if (i == null) {
            resp = false;
        } else {
            int cmp = title.compareTo(i.elemento.getTitle());
            if (cmp == 0) {
                resp = true;
            } else if (cmp < 0) {
                System.out.print("esq ");
                resp = pesquisar(title, i.esq);
            } else {
                System.out.print("dir ");
                resp = pesquisar(title, i.dir);
            }
        }
        return resp;
    }
}

public class arvoreBST {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        String path = "/tmp/disneyplus.csv";
        File arquivo = new File(path);

        ArrayList<Show> catalogo = new ArrayList<>();
        Scanner sc = new Scanner(arquivo);
        String linha; //salva a linha de um filme para separar depois os atributos
        

        sc.nextLine(); //ignorar a primeira linha (com atributos)
        while (sc.hasNextLine()) {
            linha = sc.nextLine();
            Show s = new Show();

            try {
                s.ler(linha); //chama a função que cria um objeto a partir da linha lida
            }
            catch (Exception ex){
            }

            catalogo.add(s);
        }

        sc.close();
        Scanner scanner = new Scanner(System.in);
        String input;
        ArvoreBinaria arvore = new ArvoreBinaria();

        //buscar um filme no catalogo pelo id digitado
        while(scanner.hasNextLine()) {
            input = scanner.nextLine();

            if("FIM".equals(input)) break;

            for (Show s : catalogo) {
                if(s.getId().equals(input)){
                    try {
                        arvore.inserir(s);
                    } catch (Exception ex) {
                    }
                }
            }
        }

        // Pesquisa dos registros (Titles)
        while (true) {
            String title = scanner.nextLine();
            if (title.equals("FIM"))
                break;

            arvore.pesquisar(title);
        }

        scanner.close();
    }
}