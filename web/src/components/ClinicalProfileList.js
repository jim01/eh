import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Box,
  IconButton
} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { getClinicalProfiles, getPatient } from '../services/patientService';

function formatDate(dateString) {
  const date = new Date(dateString);
  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  }).format(date);
}

function ClinicalProfileList() {
  const [profiles, setProfiles] = useState([]);
  const [patient, setPatient] = useState(null);
  const navigate = useNavigate();
  const { patientId } = useParams();

  useEffect(() => {
    const loadData = async () => {
      try {
        const [patientResponse, profilesResponse] = await Promise.all([
          getPatient(patientId),
          getClinicalProfiles(patientId)
        ]);
        setPatient(patientResponse.data);
        setProfiles(profilesResponse.data);
      } catch (error) {
        console.error('Error loading data:', error);
      }
    };

    loadData();
  }, [patientId]);

  if (!patient) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <Paper sx={{ p: 4, maxWidth: 800, mx: 'auto', mt: 4 }}>
      <Box sx={{ display: 'flex', alignItems: 'center', mb: 4 }}>
        <IconButton onClick={() => navigate('/')} sx={{ mr: 2 }}>
          <ArrowBackIcon />
        </IconButton>
      </Box>

      <Box sx={{ mb: 4, pb: 2, borderBottom: 1, borderColor: 'divider' }}>
        <Typography variant="h5" gutterBottom>
          {patient.firstName} {patient.lastName}
        </Typography>
        <Typography variant="subtitle1" color="text.secondary">
          Date of Birth: {new Date(patient.dob).toLocaleDateString()}
        </Typography>
      </Box>

      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h6">
          Clinical Profiles
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate(`/patients/${patientId}/profiles/new`)}
        >
          Add New Clinical Profile
        </Button>
      </Box>

      {profiles?.length > 0 ? (
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Date</TableCell>
                <TableCell>Risk Level</TableCell>
                <TableCell>Clinical Indicators</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {profiles.map((profile) => (
                <TableRow 
                  key={profile.id}
                  hover
                  onClick={() => navigate(`/patients/${patientId}/profiles/${profile.id}`)}
                  sx={{ cursor: 'pointer' }}
                >
                  <TableCell>{formatDate(profile.createdAt)}</TableCell>
                  <TableCell>
                    <Box
                      sx={{
                        display: 'inline-block',
                        px: 2,
                        py: 0.5,
                        borderRadius: 1,
                        backgroundColor: profile.riskLevel === 'HIGH' ? 'error.100' :
                                       profile.riskLevel === 'MEDIUM' ? 'warning.100' :
                                       profile.riskLevel === 'LOW' ? 'success.100' : 'grey.100',
                        color: profile.riskLevel === 'HIGH' ? 'error.dark' :
                               profile.riskLevel === 'MEDIUM' ? 'warning.dark' :
                               profile.riskLevel === 'LOW' ? 'success.dark' : 'grey.dark',
                      }}
                    >
                      {profile.riskLevel?.toLowerCase()}
                    </Box>
                  </TableCell>
                  <TableCell>{profile.clinicalIndicators}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography color="text.secondary">
          No clinical profiles found
        </Typography>
      )}
    </Paper>
  );
}

export default ClinicalProfileList; 