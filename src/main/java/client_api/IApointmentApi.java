package client_api;

import data_models.Apointment;

import java.util.List;
import java.util.UUID;

public interface IApointmentApi {

	public List<Apointment> getApointment();

	public Apointment getApointment(UUID apointmentId);

	public List<Apointment> getApointment();

	public void getApointment();

}