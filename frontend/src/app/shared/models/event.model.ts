import { EventType } from './event-type.enum';

export class Event {
    id: number;
    name: string;
    description: string;
    startDate: Date;
    endDate: Date;
    public imagePaths: string[];
    public videoPaths: string[];
    public eventType: EventType;

}
