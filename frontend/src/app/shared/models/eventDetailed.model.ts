import { EventType } from './event-type.enum';
import { EventDay } from './event-day.model';

export class EventDetailed {
    id: number;
    name: string;
    description: string;
    startDate: Date;
    endDate: Date;
    imagePaths: string[];
    videoPaths: string[];
    eventType: EventType;
    eventDays: EventDay[];
    location: Location;

}
