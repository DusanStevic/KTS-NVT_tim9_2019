import { EventType } from './event-type.enum';

export class EventDetailed {
    id: number;
    name: string;
    description: string;
    startDate: Date;
    endDate: Date;
    public imagePaths: string[];
    public videoPaths: string[];
    public eventType: EventType;

}
