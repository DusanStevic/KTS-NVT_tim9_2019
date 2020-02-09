export class ReservationDTO {
    tickets: TicketDTO[];
    eventDayId: string;
    purchased: boolean;
}

export abstract class TicketDTO {
    type: string;
    eventSectorId: number;
}

export class SittingTicketDTO extends TicketDTO {
    row: number;
    col: number;
}

export class StandingTicketDTO extends TicketDTO {
    numOfStandingTickets: number;
}
