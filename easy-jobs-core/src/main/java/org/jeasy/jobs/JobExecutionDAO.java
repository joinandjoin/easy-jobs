package org.jeasy.jobs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
class JobExecutionDAO {

    private SessionFactory sessionFactory;

    public JobExecutionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(JobExecution jobExecution) {
        sessionFactory.getCurrentSession().saveOrUpdate(jobExecution);
    }

    public void update(int jobRequestId, JobExitStatus jobExitStatus, LocalDateTime endDate) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from JobExecution where requestId = :requestId ");
        query.setParameter("requestId", jobRequestId);
        JobExecution jobExecution = (JobExecution) query.getSingleResult(); // todo use TypedQuery
        jobExecution.setJobExecutionStatus(JobExecutionStatus.FINISHED);
        jobExecution.setJobExitStatus(jobExitStatus);
        jobExecution.setEndDate(endDate);
        session.update(jobExecution);
    }

}