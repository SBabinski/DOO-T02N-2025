package org.aplicacao.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class OrdemList {

    public static Comparator<ProcuraFilmeDto> compareAlfabeto() {
        return new Comparator<ProcuraFilmeDto>() {
            @Override
            public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                if (a1.getName() == null && a2.getName() == null) {
                    return 0;
                }
                if (a1.getName() == null) {
                    return 1;
                }
                if (a2.getName() == null) {
                    return -1;
                }
                return a1.getName().compareToIgnoreCase(a2.getName());
            }
        };
    }

    public static Comparator<ProcuraFilmeDto> compareNotaGeral() {
        return new Comparator<ProcuraFilmeDto>() {
            @Override
            public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                Double nota1 = 0.0;
                Double nota2 = 0.0;

                if (a1.getRating() != null && a1.getRating().getAverage() != null) {
                    nota1 = a1.getRating().getAverage();
                }
                if (a2.getRating() != null && a2.getRating().getAverage() != null) {
                    nota2 = a2.getRating().getAverage();
                }
                return nota2.compareTo(nota1);
            }
        };
    }

    public static Comparator<ProcuraFilmeDto> compareEstadoSerie() {
        return new Comparator<ProcuraFilmeDto>() {
            @Override
            public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                return getOrdemEstado(a1.getStatus()) - getOrdemEstado(a2.getStatus());


            }

            private int getOrdemEstado(String status) {
                if (status == null) return 99;

                switch (status.toLowerCase()) {
                    case "running":
                        return 0;
                    case "ended":
                        return 1;
                    case "canceled":
                        return 2;
                    default:
                        return 99;
                }
            }
        };
    }





        public static Comparator<ProcuraFilmeDto> compareDateEstreiaSerie(int opc) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            switch (opc) {
                case 1:
                    // compareDateInicioSerieDecrescente retorno decrescente do mais novo ao mais velho do get Premiered
                    return new Comparator<ProcuraFilmeDto>() {
                        @Override
                        public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                            try {
                                String p1 = a1.getPremiered();
                                String p2 = a2.getPremiered();

                                if (p1 == null && p2 == null) return 0;
                                if (p1 == null) return 1;
                                if (p1 == null) return -1;

                                LocalDate d1 = LocalDate.parse(a1.getPremiered(), formatter);
                                LocalDate d2 = LocalDate.parse(a2.getPremiered(), formatter);

                                return d2.compareTo(d1);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };

                case 2:
                    // compareDateInicioSerieCrescente retorno crescente
                    return new Comparator<ProcuraFilmeDto>() {
                        @Override
                        public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                            try {
                                String p1 = a1.getPremiered();
                                String p2 = a2.getPremiered();

                                if (p1 == null && p2 == null) return 0;
                                if (p1 == null) return 1;
                                if (p1 == null) return -1;

                                LocalDate d1 = LocalDate.parse(a1.getPremiered(), formatter);
                                LocalDate d2 = LocalDate.parse(a2.getPremiered(), formatter);

                                return d1.compareTo(d2);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                default:
                    // Caso opção inválida, retorna comparator neutro
                    return new Comparator<ProcuraFilmeDto>() {
                        @Override
                        public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                            return 0;
                        }
                    };
            }

        }

        public static Comparator<ProcuraFilmeDto> compareDateTerminoSerie(int opc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            switch (opc) {
                case 1:
                    // compareDateFinalSerieDecrescente retorno decrescente
                    return new Comparator<ProcuraFilmeDto>() {
                        @Override
                        public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                            try {
                                String p1 = a1.getEnded();
                                String p2 = a2.getEnded();

                                if (p1 == null && p2 == null) return 0;
                                if (p1 == null) return 1;
                                if (p1 == null) return -1;

                                LocalDate d1 = LocalDate.parse(a1.getEnded(), formatter);
                                LocalDate d2 = LocalDate.parse(a2.getEnded(), formatter);

                                return d2.compareTo(d1);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };

                case 2:
                    // compareDateFinalSerieCrescente retorno crescente
                    return new Comparator<ProcuraFilmeDto>() {
                        @Override
                        public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                            try {
                                String p1 = a1.getEnded();
                                String p2 = a2.getEnded();

                                if (p1 == null && p2 == null) return 0;
                                if (p1 == null) return 1;
                                if (p1 == null) return -1;

                                LocalDate d1 = LocalDate.parse(a1.getEnded(), formatter);
                                LocalDate d2 = LocalDate.parse(a2.getEnded(), formatter);
                                return d1.compareTo(d2);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                default:
                    // Caso opção inválida, retorna comparator neutro
                    return new Comparator<ProcuraFilmeDto>() {
                        @Override
                        public int compare(ProcuraFilmeDto a1, ProcuraFilmeDto a2) {
                            return 0;
                        }
                    };
            }
        }
}
