import { EventType } from './event-type.enum';

export class Event {
    id: number;
    name: string;
    startDate: Date;
    endDate: Date;
    public eventType: EventType;

}
