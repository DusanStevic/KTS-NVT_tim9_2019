import { SimpleTicket } from './simpleTicket.model';

export class ReservationDetailed {
    id: number;
    eventName: string;
    purchased: boolean;
    tickets: SimpleTicket[];
    fullPrice: number;
    username: string;
    reservationDate: Date;

}
