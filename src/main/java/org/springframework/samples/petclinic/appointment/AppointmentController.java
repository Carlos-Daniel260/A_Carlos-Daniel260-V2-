package org.springframework.samples.petclinic.appointment;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.samples.petclinic.citas.Citas;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final SpecialtieRepository specialtieRepository;
    private final VetRepository vetRepository;

    private static final String VIEW_PRODUCTO_REPORT = "appointment/appointment-report";
    private static final String VIEW_PRODUCTO_REPORT_BY_SPECIALTIE = "appointment/appointment-report-by-specialtie";
    private static final String VIEW_ADD_ESPECIALISTA = "appointment/appointment-especialista";

    public AppointmentController(AppointmentRepository appointmentRepository, SpecialtieRepository specialtieRepository, VetRepository vetRepository) {
        this.appointmentRepository = appointmentRepository;
        this.specialtieRepository = specialtieRepository;
        this.vetRepository = vetRepository;
    }

    @GetMapping("/appointments/report")
    public String initFindForm(Map<String, Object> model) {
        Collection<Specialties> allSpecialties = this.specialtieRepository.getSpecialties();
        model.put("allSpecialties", allSpecialties);
        return VIEW_PRODUCTO_REPORT;
    }

    @GetMapping("/appointment/report/{specialtieId}")
    public String report(Map<String, Object> model, @PathVariable("specialtieId") int specialtieId) {
        Collection<Appointment> allAppointments = null;
        Collection<Vet> allVets = this.vetRepository.findAll();
        model.put("allVets", allVets);
        System.out.println(specialtieId);
        if (specialtieId > 0) {
            allAppointments = this.appointmentRepository.getAppointments(specialtieId);
            model.put("allAppointments", allAppointments);
            Specialties specialtie = this.specialtieRepository.getSpecialtieById(specialtieId);
            model.put("specialtie", specialtie.getNombre());

        } else {
            if (specialtieId == 0) {
                allAppointments = this.appointmentRepository.getAppointmentsByConfirmation(0);
                model.put("specialtie", "No confirmados");
            } else if (specialtieId == -1) {
                allAppointments = this.appointmentRepository.getAppointmentsByConfirmation(1);
                model.put("specialtie", "Confirmados");
            } else {
                allAppointments = this.appointmentRepository.getAppointments();
                model.put("specialtie", "Todas las Citas");
            }
            model.put("allAppointments", allAppointments);
        }

        return VIEW_PRODUCTO_REPORT_BY_SPECIALTIE;
    }

    @GetMapping("/appointments/update/{id}")
    public String updateAppointment(@PathVariable("id") int productId) {
        System.out.println("Id cita: " + productId);
        //Appointment product = this.appointmentRepository.findById(productId);
        //System.out.println("PRO "+product);
        //this.appointmentRepository.delete(product);
        this.appointmentRepository.updateUserSetStatusForNameNative("1", productId);

        return "redirect:/appointments/report";
    }

    @GetMapping("/appointments/especialista/{id}")
    public String addEspecialista(Map<String, Object> model, @PathVariable("id") int productId) {
        System.out.println("Id cita: " + productId);
        Appointment appointment = this.appointmentRepository.findById(productId);
        Collection<Vet> allVets = this.vetRepository.findAll();
        model.put("allVets", allVets);
        model.put("appointment", appointment);
        //Appointment product = this.appointmentRepository.findById(productId);
        //System.out.println("PRO "+product);
        //this.appointmentRepository.delete(product);
        this.appointmentRepository.updateUserSetStatusForNameNative("1", productId);

        return VIEW_ADD_ESPECIALISTA;
    }

    @PostMapping("/appointment/asignarEspecialista")
    public String asignarEspecialista(Appointment appointment, BindingResult result) {
        System.out.println("Especialista:" + appointment.getEspecialista());
        System.out.println("Cita id:" + appointment.getId());
        
        this.appointmentRepository.updateUserSetEspecialista(appointment.getEspecialista(), appointment.getId());

        return "redirect:/appointment/report/-2";

    }

}
