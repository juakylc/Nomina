package nomina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author Joaquín
 */
public class Nomina {

    public static void main(String[] args) {
        System.out.print("Este es un programa para calcular la nomina. ");
        System.out.println("A continuación os pediremos los datos para calcularlas.");
        System.out.println("En caso de no existir, simplemente introducir 0.");
        System.out.println("");
        imprimirNomina();
    }   
    
    public static float leerFloat(String introduce) {    
        Scanner in = new Scanner(System.in);
        System.out.println(introduce);
        return in.nextFloat();
    } 
    
    public static float tomarDosDecimales(float n) {
        int m  = (int) (n*100);
        n = m/100.f;     
        return n;
    }
    
    public static float calculoAntiguedad(float salarioBase) {
        float anosantiguedad = leerFloat("Introduce el numero de años en la empresa: ");
        float porcentaje = leerFloat("Introduce el numero del porcentaje del aumento por antiguedad: ");
        float cadaXanos = leerFloat("Introduce cada cuantos años se aplica el aumento por antiguedad: ");
        
        return tomarDosDecimales((porcentaje/100 * salarioBase)*((int) (anosantiguedad/cadaXanos)));
    }
    
    public static float calculoProrrota(float n, float m) {
        
        return tomarDosDecimales(n*m/12);
    }
    
    public static void imprimirSiExiste(String string, float n) {
        if (n != 0) {
            System.out.println(string + n);
        }
    }
    
    public static void imprimirSuma(String string, float n) {
        if (n != 0) {
            System.out.print(n + "(" + string +") + ");
        }
    }
    
    public static void imprimirSuma(String string, float n, float res) {
        if (n != 0) {
            System.out.println(n + "(" + string +") = " + res);
        } else {
            System.out.println(" = " + res);
        }
    }
    
    public static float restaNZ(float n, float m) {
        if (n==0) {
            return 0;
        } else {
            return tomarDosDecimales(n-m);
        }
    }
    
    public static void imprimirNomina() {
        //percepciones salariales. sumaplus y prorrota esta despues de todo para que se entienda mejor
        float salarioBase = leerFloat("Introduce el Salario Base: ");
        float antiguedad = calculoAntiguedad(salarioBase);
        float sumIncentivos = leerFloat("Introduce la suma de todos los incentivos: ");
        float horasExtrasFM = leerFloat("Introduce las horas extras por Fuerza Mayor: ");
        float horasExtras = leerFloat("Introduce las horas extras: ");
        float participacionEnBeneficios = leerFloat("Introduce las participacion en beneficios: ");
        
        //percepciones no salariales
        float transporte = leerFloat("Introduce el plus de transporte: ");
        float estancia = leerFloat("Introduce el gasto en Estancia: ");
        float manPerESP = leerFloat("Introduce el gasto en Manutencion Pernocta en España: ");
        float manPerEXT = leerFloat("Introduce el gasto en Manutencion Pernocta en Extranjero: ");
        float manNoPerESP = leerFloat("Introduce el gasto en Manutencion No Pernocta en España: ");
        float manNoPerEXT = leerFloat("Introduce el gasto en Manutencion No Pernocta en Extranjero: ");
        float nDias = leerFloat("Introduce el numero de dias de manutencion: ");
        float manutencion = manPerESP + manPerEXT + manNoPerESP + manNoPerEXT;
        float dieta = estancia + manutencion;
        float locomocionPub = leerFloat("Introduce el gasto en locomocion en transporte publico: ");
        System.out.println("Gasto de locomocion en transporte privado: ");
        float precio = 0.25f;//leerFloat("Introduce el precio por km: ");
        float km = leerFloat("Introduce el numero de kms: ");
        float locomocionPri = precio * km;
        float quebranto = leerFloat("Introduce el quebranto de moneda: ");
        float herramientas = leerFloat("Introduce el plus de herramientas: ");
        float ropa = leerFloat("Introduce el plus de ropa: ");
        
        //importantes
        float sumaplus = leerFloat("Introduce la suma de los demas plus como peligrosidad, toxicidad, turnicidad y nocturnidad...: ");
        float irpf = leerFloat("Introduce el numero del tipo de retención de IRPF: ");
        float n = leerFloat("nº de pagas extras: ");
        float m = leerFloat("Importe de Pagas Extra: ");
        
        //resultados
        float totalDevengado = salarioBase + antiguedad + sumIncentivos + horasExtrasFM + horasExtras + participacionEnBeneficios + sumaplus + transporte + dieta + locomocionPub + locomocionPri + quebranto + herramientas + ropa;
        float percepcionesSalariales = salarioBase + antiguedad + sumIncentivos + participacionEnBeneficios + sumaplus;
        float percepcionesNoSalariales = transporte + ropa + herramientas + quebranto + (precio-0.19f)*km + restaNZ(manPerESP,nDias*53.34f) + restaNZ(manNoPerESP,nDias*26.67f) + restaNZ(manPerEXT,nDias*91.35f) + restaNZ(manNoPerEXT,nDias*48.08f);
        float prorrata = calculoProrrota(n,m);
        float bccc = percepcionesSalariales + percepcionesNoSalariales + prorrata;
        float bccp = bccc + horasExtras + horasExtrasFM;
        float birpf = tomarDosDecimales(bccp - prorrata);
        
        float bcccDed = tomarDosDecimales(bccc * 0.047f);
        float desempleo = tomarDosDecimales(bccp * 0.0155f);
        float fp = tomarDosDecimales(bccp * 0.001f);
        float horasExtrasDed = tomarDosDecimales(horasExtras * 0.047f);
        float horasExtrasFMDed = tomarDosDecimales(horasExtrasFM * 0.02f);
        float irpfDed = tomarDosDecimales(birpf * irpf/100);
        float deducciones = tomarDosDecimales(bcccDed + desempleo + fp + horasExtrasDed + horasExtrasFMDed + irpfDed);
        float salarioLiquido = totalDevengado - deducciones;
        
        System.out.println("");
        System.out.println("Datos:");
        System.out.println("");
        imprimirSiExiste("Salario Base: ", salarioBase);
        imprimirSiExiste("Antiguedad: ", antiguedad);
        imprimirSiExiste("Incentivos: ", sumIncentivos);
        imprimirSiExiste("Horas Extras Fuerza Mayor: ", horasExtrasFM);
        imprimirSiExiste("Horas Extras: ", horasExtras);
        imprimirSiExiste("Participacion en Beneficios: ", participacionEnBeneficios);
        imprimirSiExiste("Plus de Transporte: ", transporte);
        imprimirSiExiste("Dieta: ", dieta);
        imprimirSiExiste("Gasto en Locomocion en transporte publico: ", locomocionPub);
        imprimirSiExiste("Gasto en Locomocion en transporte privado: ", locomocionPri);
        imprimirSiExiste("Quebranto de Moneda: ", quebranto);
        imprimirSiExiste("Plus de Herramientas: ", herramientas);
        imprimirSiExiste("Plus de ropa: ", ropa);
        imprimirSiExiste("Resto de plus: ", sumaplus);
        imprimirSiExiste("Porcentaje de IRPF: ", irpf);
        imprimirSiExiste(n+" Pagas Extraordinarias, cada una con valor: ",m);
        System.out.println("");
        
        System.out.print("Total Devengado = ");
        imprimirSuma("Salario Base", salarioBase);
        imprimirSuma("Antiguedad", antiguedad);
        imprimirSuma("Incentivos", sumIncentivos);
        imprimirSuma("Horas Extras Fuerza Mayor", horasExtrasFM);
        imprimirSuma("Horas Extras", horasExtras);
        imprimirSuma("Participacion en Beneficios", participacionEnBeneficios);
        imprimirSuma("Plus de Transporte", transporte);
        imprimirSuma("Dieta", dieta);
        imprimirSuma("Gasto en Locomocion en transporte publico", locomocionPub);
        imprimirSuma("Gasto en Locomocion en transporte privado", locomocionPri);
        imprimirSuma("Quebranto de Moneda", quebranto);
        imprimirSuma("Plus de Herramientas", herramientas);
        imprimirSuma("Plus de ropa", ropa);
        imprimirSuma("Plus", sumaplus, totalDevengado);
        
        System.out.println("");
        System.out.println("Base de Cotizacion de Contingencias Comunes: ");
        System.out.println("");
        System.out.print("Percepciones Salariales: ");
        imprimirSuma("Salario Base", salarioBase);
        imprimirSuma("Antiguedad", antiguedad);
        imprimirSuma("Incentivos", sumIncentivos);
        imprimirSuma("Participacion en Beneficios", participacionEnBeneficios);
        imprimirSuma("Plus", sumaplus, percepcionesSalariales);
        System.out.print("Percepciones No Salariales: ");
        imprimirSuma("Plus de Transporte", transporte);
        imprimirSuma("Quebranto de Moneda", quebranto);
        imprimirSuma("Plus de Herramientas", herramientas);
        imprimirSuma("Plus de ropa", ropa);
        imprimirSuma("Plus de ropa", ropa, percepcionesNoSalariales);
        System.out.println("Prorrata: (" + n + "x" + m + ") / 12 = " + prorrata);
        System.out.println("Total: " + bccc);
        System.out.println("");
        System.out.print("Base De Cotizacion de Contingencias Profesionales = ");
        imprimirSuma("BCCC", bccc);
        imprimirSuma("Horas Extras Fuerza Mayor", horasExtrasFM);
        imprimirSuma("Horas Extras", horasExtras, bccp);

        System.out.println("");
        System.out.println("Base sujeta a retención IRPF: " + bccp + "(BCCP) - " + prorrata + "(PPE) = " + birpf); 
        
        System.out.println("");
        System.out.println("Deducciones:");
        System.out.println("");
        System.out.println("BCCC: 4.7% de " + bccc + " = " + bcccDed);
        System.out.println("Desempleo: 1.55% de " + bccp + " = " + desempleo);
        System.out.println("FP: 0.1% de " + bccp + " = " + fp);
        System.out.println("Horas Extras: 4.7% de " + horasExtras + " = " + horasExtrasDed);
        System.out.println("Horas Extras Fuerza Mayor: 2% de " + horasExtrasFM + " = " + horasExtrasFMDed);
        System.out.println("IRPF: " + irpf + "% de " + birpf + " = " + irpfDed);
        System.out.println("Total: " + deducciones);
        System.out.println("");
        System.out.println("Salario Liquido = " + totalDevengado + " - " + deducciones + " = " + salarioLiquido);
    }
}
