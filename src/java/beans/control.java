
package beans;

import controller.ArticuloreglamJpaController;
import controller.AspectEvalcuartelJpaController;
import controller.AspectEvalcuartoEvalcuartoJpaController;
import controller.AspectEvalcuartoJpaController;
import controller.BecadoJpaController;
import controller.CapituloreglamJpaController;
import controller.CuartoJpaController;
import controller.EdificioJpaController;
import controller.EvalcuarteleriaAspectevalcuartelJpaController;
import controller.EvalcuarteleriaJpaController;
import controller.EvalcuartoJpaController;
import controller.IncisoreglamJpaController;
import controller.PisoJpaController;
import controller.TrabajoprodJpaController;
import controller.TrabajadorJpaController;
import controller.MedicamentosJpaController;
import controller.EnfermedadesJpaController;
import controller.UsersJpaController;
import controller.TipoevalbecadoJpaController;
import controller.EvalguardiaJpaController;
import controller.RangosJpaController;
import controller.MedicoJpaController;
import controller.EvalbecadoJpaController;
import controller.PacientesatendidosJpaController;
import controller.SancionesJpaController;
import controller.PacatendMedicamentosJpaController;
import controller.PlanteamientosJpaController;
import controller.AuthoritiesJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class control {
    
    
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sigrePU");
    
    public static EdificioJpaController edificioJPA = new EdificioJpaController(emf);
    public static PisoJpaController pisoJPA = new PisoJpaController(emf);
    public static CuartoJpaController cuartoJPA = new CuartoJpaController(emf);
    public static TrabajoprodJpaController trabProdJPA = new TrabajoprodJpaController(emf);
    
    public static EvalcuartoJpaController evalCuartoJPA = new EvalcuartoJpaController(emf);
    public static AspectEvalcuartoJpaController aspectevalCuartoJPA = new AspectEvalcuartoJpaController(emf);
    public static AspectEvalcuartoEvalcuartoJpaController aspEv_EvCuartoJPA = new AspectEvalcuartoEvalcuartoJpaController(emf);
    
    public static TrabajadorJpaController trabajadorJPA = new TrabajadorJpaController(emf);
    public static BecadoJpaController becadoJPA = new BecadoJpaController(emf);
    
    public static CapituloreglamJpaController capituloReglamJPA = new CapituloreglamJpaController(emf);
    public static ArticuloreglamJpaController articuloReglamJPA = new ArticuloreglamJpaController(emf);
    public static IncisoreglamJpaController incisoReglamJPA = new IncisoreglamJpaController(emf);
    
    public static AspectEvalcuartelJpaController aspectevalCuartelJPA = new AspectEvalcuartelJpaController(emf);
    public static EvalcuarteleriaAspectevalcuartelJpaController aspEvCuartel_EvCuartelJPA = new EvalcuarteleriaAspectevalcuartelJpaController(emf);
    public static EvalcuarteleriaJpaController evalCuarteleriaJPA = new EvalcuarteleriaJpaController(emf);
    public static MedicamentosJpaController medicamentosJpa = new MedicamentosJpaController(emf);
    
    public static EnfermedadesJpaController enfermedadesJpa = new EnfermedadesJpaController(emf);
    public static UsersJpaController usersJpa = new UsersJpaController(emf);
    public static TipoevalbecadoJpaController tipoevalbecadoJpa = new TipoevalbecadoJpaController(emf);
    public static EvalguardiaJpaController evalguardiaJpa = new EvalguardiaJpaController(emf);
    public static RangosJpaController rangosJpa = new RangosJpaController(emf);
    public static MedicoJpaController medicoJpa = new MedicoJpaController(emf);
    public static PacientesatendidosJpaController pacientesatendidosJpa = new PacientesatendidosJpaController(emf);
    public static EvalbecadoJpaController evalbecadoJpa = new EvalbecadoJpaController(emf);
    public static AspectEvalcuartoEvalcuartoJpaController aspectEvalcuartoEvalcuartoJpa = new AspectEvalcuartoEvalcuartoJpaController(emf);
    public static SancionesJpaController sancionesJpa = new SancionesJpaController(emf);
    public static PacatendMedicamentosJpaController pacatendMedicamentosJpa = new PacatendMedicamentosJpaController(emf);
    public static PlanteamientosJpaController planteamientosJpa = new PlanteamientosJpaController(emf);
    public static AuthoritiesJpaController authoritiesJpa = new AuthoritiesJpaController(emf);
}
