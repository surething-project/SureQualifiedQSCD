package main.kotlin.qscd.services

import main.kotlin.qscd.controllers.SenderAndSessionData
import main.kotlin.qscd.repositories.CitizenCardRepository
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.CitizenCard
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.CitizenCardSigned

class CitizenCardService(
    private val citizenCardRepository: CitizenCardRepository,
    private val sessionService: SessionService
) {

    fun registerCitizenCard(citizenCard: CitizenCard, senderId: String) {
        val session = sessionService.getDefaultSession()
        citizenCardRepository.registerCitizenCard(citizenCard, senderId, session)
    }

    fun registerCitizenCard(citizenCard: CitizenCard, senderAndSessionData: SenderAndSessionData) {
        val session = sessionService.getSession(senderAndSessionData.sessionId)
        citizenCardRepository.registerCitizenCard(citizenCard, senderAndSessionData.senderId, session)
    }

    fun registerCitizenCardSigned(citizenCardSigned: CitizenCardSigned, senderAndSessionData: SenderAndSessionData) {
        val session = sessionService.getSession(senderAndSessionData.sessionId)
        citizenCardRepository.registerCitizenCardSigned(citizenCardSigned, senderAndSessionData.senderId, session)
    }
}