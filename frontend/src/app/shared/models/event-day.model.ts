import { EventStatus } from './event-status.enum';

export class EventDay {
    id: number;
    name: string;
    date: Date;
    description: string;
    eventStatus: EventStatus;
}
