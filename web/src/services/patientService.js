import axios from 'axios';

const API_URL = '/api';

export const getPatients = () => {
  return axios.get(`${API_URL}/patients`);
};

export const getPatient = (patientId) => {
  return axios.get(`${API_URL}/patients/${patientId}`);
}; 

export const createPatient = (patientData) => {
  return axios.post(`${API_URL}/patients`, patientData);
};

export const getClinicalProfiles = (patientId) => {
  return axios.get(`${API_URL}/clinical-profiles/patient/${patientId}`);
};

export const createClinicalProfile = (patientId, profileData) => {
  return axios.post(`${API_URL}/clinical-profiles`, profileData);
};

export const getClinicalProfile = (profileId) => {
  return axios.get(`${API_URL}/clinical-profiles/${profileId}`);
};
