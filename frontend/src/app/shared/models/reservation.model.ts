export class ReservationDTO {
    sittingTickets: SittingTicketDTO[];
    standingTickets: StandingTicketDTO[];
    eventDayId: number;
    purchased: boolean;
}

export abstract class TicketDTO {
    type: string;
    eventSectorId: number;
}

export class SittingTicketDTO {
    row: number;
    col: number;
    type: string;
    eventSectorId: number;
}

export class StandingTicketDTO {
    numOfStandingTickets: number;
    type: string;
    eventSectorId: number;
}
