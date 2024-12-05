import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  Paper,
  Typography,
  Box,
  IconButton,
  Divider,
  Grid,
  Chip
} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { getClinicalProfile, getPatient } from '../services/patientService';

function formatDate(dateString) {
  const date = new Date(dateString);
  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  }).format(date);
}

function ClinicalProfileDetails() {
  const [profile, setProfile] = useState(null);
  const [patient, setPatient] = useState(null);
  const navigate = useNavigate();
  const { patientId, profileId } = useParams();

  useEffect(() => {
    const loadData = async () => {
      try {
        const [patientResponse, profileResponse] = await Promise.all([
          getPatient(patientId),
          getClinicalProfile(profileId)
        ]);
        setPatient(patientResponse.data);
        setProfile(profileResponse.data);
      } catch (error) {
        console.error('Error loading data:', error);
      }
    };

    loadData();
  }, [patientId, profileId]);

  if (!profile || !patient) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <Paper sx={{ p: 4, maxWidth: 800, mx: 'auto', mt: 4 }}>
      <Box sx={{ display: 'flex', alignItems: 'center', mb: 4 }}>
        <IconButton onClick={() => navigate(`/patients/${patientId}/profiles`)} sx={{ mr: 2 }}>
          <ArrowBackIcon />
        </IconButton>
        <Box>
          <Typography variant="h5" gutterBottom>
            {patient.firstName} {patient.lastName}
          </Typography>
          <Typography variant="subtitle1" color="text.secondary">
            Date of Birth: {formatDate(patient.dob)}
          </Typography>
        </Box>
      </Box>

      <Divider sx={{ mb: 4 }} />

      <Box sx={{ mb: 4 }}>
        <Typography variant="subtitle2" color="text.secondary">
          Profile Created
        </Typography>
        <Typography variant="body1" gutterBottom>
          {formatDate(profile.createdAt)} {new Date(profile.createdAt).toLocaleTimeString()}
        </Typography>
      </Box>

      <Grid container spacing={3}>
        {/* Physical Measurements */}
        <Grid item xs={12}>
          <Typography variant="h6" gutterBottom>Physical Measurements</Typography>
          <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: 2 }}>
            <Box>
              <Typography variant="subtitle2" color="text.secondary">BMI</Typography>
              <Typography>{profile.bmi || 'Not recorded'}</Typography>
            </Box>
            <Box>
              <Typography variant="subtitle2" color="text.secondary">Weight Loss</Typography>
              <Typography>{profile.weightLoss ? `${profile.weightLoss} kg` : 'Not recorded'}</Typography>
            </Box>
            <Box>
              <Typography variant="subtitle2" color="text.secondary">Blood Pressure</Typography>
              <Typography>{profile.bloodPressure || 'Not recorded'}</Typography>
            </Box>
            <Box>
              <Typography variant="subtitle2" color="text.secondary">Heart Rate</Typography>
              <Typography>{profile.heartRate ? `${profile.heartRate} bpm` : 'Not recorded'}</Typography>
            </Box>
          </Box>
        </Grid>

        {/* Clinical Assessment */}
        <Grid item xs={12}>
          <Divider sx={{ my: 2 }} />
          <Typography variant="h6" gutterBottom>Clinical Assessment</Typography>
          <Box sx={{ mb: 3 }}>
            <Typography variant="subtitle2" color="text.secondary">Clinical Indicators</Typography>
            <Typography>{profile.clinicalIndicators || 'None recorded'}</Typography>
          </Box>
          <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: 2 }}>
            <Box>
              <Typography variant="subtitle2" color="text.secondary">ED Diagnosis</Typography>
              <Typography>{profile.edDiagnosis || 'Not recorded'}</Typography>
            </Box>
            <Box>
              <Typography variant="subtitle2" color="text.secondary">ED Score</Typography>
              <Typography>{profile.edScore || 'Not recorded'}</Typography>
            </Box>
          </Box>
        </Grid>

        {/* Risk Assessment */}
        <Grid item xs={12}>
          <Divider sx={{ my: 2 }} />
          <Typography variant="h6" gutterBottom>Risk Assessment</Typography>
          <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: 2 }}>
            <Box>
              <Typography variant="subtitle2" color="text.secondary">Risk Level</Typography>
              <Chip
                label={profile.riskLevel?.toLowerCase() || 'Not set'}
                color={
                  profile.riskLevel === 'HIGH' ? 'error' :
                  profile.riskLevel === 'MEDIUM' ? 'warning' :
                  profile.riskLevel === 'LOW' ? 'success' : 'default'
                }
                size="small"
                sx={{ mt: 0.5 }}
              />
            </Box>
            <Box>
              <Typography variant="subtitle2" color="text.secondary">Risk Score</Typography>
              <Typography>{profile.riskScore || 'Not recorded'}</Typography>
            </Box>
          </Box>
        </Grid>

        {/* Behaviors */}
        <Grid item xs={12}>
          <Divider sx={{ my: 2 }} />
          <Typography variant="h6" gutterBottom>Behaviors</Typography>
          <Typography>{profile.behaviors || 'None recorded'}</Typography>
        </Grid>

        {/* Goals and Interventions */}
        <Grid item xs={12}>
          <Divider sx={{ my: 2 }} />
          <Typography variant="h6" gutterBottom>Goals and Interventions</Typography>
          {profile.goals?.map((goal, index) => (
            <Box 
              key={index}
              sx={{ 
                mb: 3,
                p: 2,
                border: 1,
                borderColor: 'divider',
                borderRadius: 1
              }}
            >
              <Box sx={{ mb: 2 }}>
                <Typography variant="subtitle1" gutterBottom>
                  {goal.name}
                </Typography>
                <Chip 
                  label={goal.type?.toLowerCase()} 
                  size="small" 
                  sx={{ mb: 1 }}
                />
                <Typography variant="body2" color="text.secondary">
                  {goal.description}
                </Typography>
              </Box>

              {goal.interventions?.length > 0 && (
                <Box sx={{ ml: 2 }}>
                  <Typography variant="subtitle2" gutterBottom>
                    Interventions
                  </Typography>
                  {goal.interventions.map((intervention, iIndex) => (
                    <Box 
                      key={iIndex}
                      sx={{ 
                        mb: 1,
                        p: 1,
                        backgroundColor: 'action.hover',
                        borderRadius: 1
                      }}
                    >
                      <Typography variant="body2" component="span" sx={{ mr: 1 }}>
                        <strong>{intervention.type}:</strong>
                      </Typography>
                      <Typography variant="body2" component="span">
                        {intervention.description}
                      </Typography>
                    </Box>
                  ))}
                </Box>
              )}
            </Box>
          ))}
        </Grid>
      </Grid>
    </Paper>
  );
}

export default ClinicalProfileDetails; 