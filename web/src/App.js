import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Container } from '@mui/material';
import PatientList from './components/PatientList';
import PatientForm from './components/PatientForm';
import ClinicalProfileList from './components/ClinicalProfileList';
import ClinicalProfileForm from './components/ClinicalProfileForm';
import ClinicalProfileDetails from './components/ClinicalProfileDetails';

function App() {
  return (
    <Router>
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Routes>
          <Route path="/" element={<PatientList />} />
          <Route path="/patients/new" element={<PatientForm />} />
          <Route path="/patients/:patientId/profiles" element={<ClinicalProfileList />} />
          <Route path="/patients/:patientId/profiles/new" element={<ClinicalProfileForm />} />
          <Route path="/patients/:patientId/profiles/:profileId" element={<ClinicalProfileDetails />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App; 