create table if not exists clinical_profiles
(
    id                  uuid not null
        primary key,
    behaviors           varchar(255),
    blood_pressure      varchar(255),
    bmi                 double precision,
    clinical_indicators varchar(255),
    created_at          timestamp(6),
    ed_diagnosis        varchar(255),
    ed_score            integer,
    heart_rate          integer,
    patient_id          uuid,
    risk_level          varchar(255),
    risk_score          double precision,
    updated_at          timestamp(6),
    weight_loss         double precision,
    clinician_id        uuid,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

create table if not exists goals
(
    id                  uuid not null
        primary key,
    clinical_profile_id uuid not null,
    created_at          timestamp(6),
    description         varchar(255),
    name                varchar(255),
    type                varchar(255),
    updated_at          timestamp(6),
    clinician_id        uuid,
    FOREIGN KEY (clinical_profile_id) REFERENCES clinical_profiles(id)
);

create table if not exists interventions
(
    id uuid         not null
        primary key,
    created_at      timestamp(6) not null,
    description     varchar(255),
    goal_id         uuid         not null,
    type            varchar(255),
    updated_at      timestamp(6) not null,
    clinician_id    uuid,
    FOREIGN KEY (goal_id) REFERENCES goals(id)
);

create table if not exists patients
(
    id             uuid not null
        primary key,
    city           varchar(255),
    created_at     timestamp(6),
    dob            varchar(255),
    first_name     varchar(255),
    last_name      varchar(255),
    postal_code    varchar(255),
    state          varchar(255),
    street_address varchar(255),
    updated_at     timestamp(6),
    email          varchar(255),
    phone          varchar(255)
);

create table if not exists assessments
(
    id                      uuid         not null
        primary key,
    clinician_id            uuid,
    created_at              timestamp(6) not null,
    date                    timestamp(6),
    intervention_id         uuid         not null,
    medical_risk_notes      varchar(255),
    progress_score          double precision,
    suicidal_ideation_score double precision,
    updated_at              timestamp(6) not null,
    FOREIGN KEY (intervention_id) REFERENCES interventions(id)
);


