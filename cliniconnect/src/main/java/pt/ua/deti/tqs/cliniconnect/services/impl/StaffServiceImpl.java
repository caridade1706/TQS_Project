package pt.ua.deti.tqs.cliniconnect.services.impl;

import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.Staff;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.StaffRepository;
import pt.ua.deti.tqs.cliniconnect.services.StaffService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService  {

    private StaffRepository staffRepository;
    private HospitalRepository hospitalRepository;

    @Override
    public Staff getPatientByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public boolean updateStaffHospitals(String email, List<String> hospitals) {
        Staff staff = staffRepository.findByEmail(email);
        if (staff != null) {
            Set<Hospital> hospitalsSet = new HashSet<>();

            for (String hospitalName : hospitals) {
                Optional<Hospital> hospital = hospitalRepository.findByName(hospitalName);
                if (hospital.isPresent()) {
                    Hospital foundHospital = hospital.get();
                    hospitalsSet.add(foundHospital);
                    foundHospital.getStaffs().add(staff);  // Add staff to hospital's staffs set
                }
            }

            staff.setHospitals(hospitalsSet);
            staffRepository.save(staff);
            return true;
        }
        return false;
    }
}