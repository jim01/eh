import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  Button,
  TextField,
  Paper,
  Typography,
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  IconButton
} from '@mui/material';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import { createClinicalProfile } from '../services/patientService';

function ClinicalProfileForm() {
  const navigate = useNavigate();
  const { patientId } = useParams();
  const [formData, setFormData] = useState({
    bmi: '',
    weightLoss: '',
    bloodPressure: '',
    heartRate: '',
    clinicalIndicators: '',
    edDiagnosis: '',
    edScore: '',
    riskLevel: '',
    riskScore: '',
    behaviors: '',
    goals: [{
      name: '',
      description: '',
      type: '',
      interventions: [{ type: '', description: '' }]
    }],
    patientId: patientId
  });

  const handleChange = (e) => {
    const value = e.target.type === 'number' 
      ? e.target.value === '' ? '' : Number(e.target.value)
      : e.target.value;
      
    setFormData({
      ...formData,
      [e.target.name]: value
    });
  };

  const handleGoalChange = (index, field, value) => {
    const newGoals = [...formData.goals];
    newGoals[index] = {
      ...newGoals[index],
      [field]: value
    };
    setFormData({
      ...formData,
      goals: newGoals
    });
  };

  const handleInterventionChange = (goalIndex, interventionIndex, field, value) => {
    const newGoals = [...formData.goals];
    newGoals[goalIndex].interventions[interventionIndex] = {
      ...newGoals[goalIndex].interventions[interventionIndex],
      [field]: value
    };
    setFormData({
      ...formData,
      goals: newGoals
    });
  };

  const addIntervention = (goalIndex) => {
    const newGoals = [...formData.goals];
    newGoals[goalIndex].interventions.push({ type: '', description: '' });
    setFormData({
      ...formData,
      goals: newGoals
    });
  };

  const removeIntervention = (goalIndex, interventionIndex) => {
    const newGoals = [...formData.goals];
    newGoals[goalIndex].interventions = newGoals[goalIndex].interventions.filter((_, i) => i !== interventionIndex);
    setFormData({
      ...formData,
      goals: newGoals
    });
  };

  const addGoal = () => {
    setFormData({
      ...formData,
      goals: [...formData.goals, {
        name: '',
        description: '',
        type: '',
        interventions: [{ type: '', description: '' }]
      }]
    });
  };

  const removeGoal = (index) => {
    const newGoals = formData.goals.filter((_, i) => i !== index);
    setFormData({
      ...formData,
      goals: newGoals
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createClinicalProfile(patientId, formData);
      navigate(`/patients/${patientId}/profiles`);
    } catch (error) {
      console.error('Error creating clinical profile:', error);
    }
  };

  return (
    <Paper sx={{ p: 4, maxWidth: 800, mx: 'auto', mt: 4 }}>
      <Typography variant="h5" gutterBottom>
        New Clinical Profile
      </Typography>
      <Box component="form" onSubmit={handleSubmit}>
        {/* Physical Measurements */}
        <Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 2, my: 2 }}>
          <TextField
            label="BMI"
            name="bmi"
            type="number"
            value={formData.bmi}
            onChange={handleChange}
            inputProps={{ step: "0.1" }}
          />
          <TextField
            label="Weight Loss (kg)"
            name="weightLoss"
            type="number"
            value={formData.weightLoss}
            onChange={handleChange}
            inputProps={{ step: "0.1" }}
          />
          <TextField
            label="Blood Pressure"
            name="bloodPressure"
            value={formData.bloodPressure}
            onChange={handleChange}
            placeholder="120/80"
          />
          <TextField
            label="Heart Rate"
            name="heartRate"
            type="number"
            value={formData.heartRate}
            onChange={handleChange}
          />
        </Box>

        <TextField
          fullWidth
          margin="normal"
          label="Clinical Indicators"
          name="clinicalIndicators"
          value={formData.clinicalIndicators}
          onChange={handleChange}
          multiline
          rows={2}
        />

        {/* ED Assessment */}
        <Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 2, my: 2 }}>
          <TextField
            label="ED Diagnosis"
            name="edDiagnosis"
            value={formData.edDiagnosis}
            onChange={handleChange}
          />
          <TextField
            label="ED Score"
            name="edScore"
            type="number"
            value={formData.edScore}
            onChange={handleChange}
          />
        </Box>

        {/* Risk Assessment */}
        <Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 2, my: 2 }}>
          <FormControl fullWidth>
            <InputLabel>Risk Level</InputLabel>
            <Select
              name="riskLevel"
              value={formData.riskLevel}
              onChange={handleChange}
              label="Risk Level"
            >
              <MenuItem value="LOW">Low</MenuItem>
              <MenuItem value="MEDIUM">Medium</MenuItem>
              <MenuItem value="HIGH">High</MenuItem>
              <MenuItem value="SEVERE">Severe</MenuItem>
            </Select>
          </FormControl>
          <TextField
            label="Risk Score"
            name="riskScore"
            type="number"
            value={formData.riskScore}
            onChange={handleChange}
            inputProps={{ step: "0.1" }}
          />
        </Box>

        <TextField
          fullWidth
          margin="normal"
          label="Behaviors"
          name="behaviors"
          value={formData.behaviors}
          onChange={handleChange}
          multiline
          rows={3}
        />

        {/* Goals Section */}
        <Box sx={{ mt: 4 }}>
          <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
            <Typography variant="h6">Goals</Typography>
            <IconButton 
              color="primary" 
              onClick={addGoal}
              sx={{ ml: 2 }}
            >
              <AddCircleOutlineIcon />
            </IconButton>
          </Box>

          {formData.goals.map((goal, goalIndex) => (
            <Box 
              key={goalIndex} 
              sx={{ 
                p: 2, 
                mb: 2, 
                border: 1, 
                borderColor: 'divider', 
                borderRadius: 1,
                position: 'relative'
              }}
            >
              <Box sx={{ display: 'grid', gap: 2 }}>
                <Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 2 }}>
                  <TextField
                    fullWidth
                    label="Goal Name"
                    value={goal.name}
                    onChange={(e) => handleGoalChange(goalIndex, 'name', e.target.value)}
                    required
                  />
                  <FormControl fullWidth>
                    <InputLabel>Goal Type</InputLabel>
                    <Select
                      value={goal.type}
                      onChange={(e) => handleGoalChange(goalIndex, 'type', e.target.value)}
                      label="Goal Type"
                      required
                    >
                      <MenuItem value="BEHAVIORAL">Behavioral</MenuItem>
                      <MenuItem value="NUTRITIONAL">Nutritional</MenuItem>
                      <MenuItem value="PHYSICAL">Physical</MenuItem>
                      <MenuItem value="PSYCHOLOGICAL">Psychological</MenuItem>
                    </Select>
                  </FormControl>
                </Box>
                <TextField
                  fullWidth
                  label="Goal Description"
                  value={goal.description}
                  onChange={(e) => handleGoalChange(goalIndex, 'description', e.target.value)}
                  multiline
                  rows={2}
                  required
                />

                {/* Interventions Section */}
                <Box sx={{ mt: 2 }}>
                  <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 1 }}>
                    <Typography variant="subtitle1">Interventions</Typography>
                    <IconButton 
                      size="small"
                      color="primary" 
                      onClick={() => addIntervention(goalIndex)}
                    >
                      <AddCircleOutlineIcon />
                    </IconButton>
                  </Box>

                  {goal.interventions.map((intervention, interventionIndex) => (
                    <Box 
                      key={interventionIndex}
                      sx={{ 
                        p: 1.5,
                        mb: 1,
                        border: 1,
                        borderColor: 'divider',
                        borderRadius: 1,
                        position: 'relative',
                        backgroundColor: 'background.paper'
                      }}
                    >
                      <Box sx={{ display: 'grid', gridTemplateColumns: '1fr 2fr', gap: 2 }}>
                        <FormControl fullWidth>
                          <InputLabel>Type</InputLabel>
                          <Select
                            value={intervention.type}
                            onChange={(e) => handleInterventionChange(goalIndex, interventionIndex, 'type', e.target.value)}
                            label="Type"
                            required
                            size="small"
                          >
                            <MenuItem value="MEDICATION">Medication</MenuItem>
                            <MenuItem value="THERAPY">Therapy</MenuItem>
                            <MenuItem value="EXERCISE">Exercise</MenuItem>
                            <MenuItem value="DIET">Diet</MenuItem>
                            <MenuItem value="OTHER">Other</MenuItem>
                          </Select>
                        </FormControl>
                        <TextField
                          fullWidth
                          size="small"
                          label="Description"
                          value={intervention.description}
                          onChange={(e) => handleInterventionChange(goalIndex, interventionIndex, 'description', e.target.value)}
                          required
                        />
                      </Box>
                      {goal.interventions.length > 1 && (
                        <IconButton 
                          size="small"
                          onClick={() => removeIntervention(goalIndex, interventionIndex)}
                          sx={{ position: 'absolute', top: 4, right: 4 }}
                          color="error"
                        >
                          <DeleteOutlineIcon fontSize="small" />
                        </IconButton>
                      )}
                    </Box>
                  ))}
                </Box>
              </Box>
              {formData.goals.length > 1 && (
                <IconButton 
                  onClick={() => removeGoal(goalIndex)}
                  sx={{ position: 'absolute', top: 8, right: 8 }}
                  color="error"
                >
                  <DeleteOutlineIcon />
                </IconButton>
              )}
            </Box>
          ))}
        </Box>

        <Box sx={{ mt: 3, display: 'flex', gap: 2 }}>
          <Button 
            variant="contained" 
            color="primary" 
            type="submit"
          >
            Save Profile
          </Button>
          <Button 
            variant="outlined" 
            onClick={() => navigate(`/patients/${patientId}/profiles`)}
          >
            Cancel
          </Button>
        </Box>
      </Box>
    </Paper>
  );
}

export default ClinicalProfileForm; 