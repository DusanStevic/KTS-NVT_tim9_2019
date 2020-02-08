import { EventType } from './event-type.enum';

export class Search {
    name: string;
    startDate: Date;
    endDate: Date;
    eventType: EventType;
    locationId: number;
}
