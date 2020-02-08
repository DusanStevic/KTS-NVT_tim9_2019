export class CreateEventDTO {
    name: string;
    description: string;
    eventType: number;
    startDate: Date;
    endDate: Date;
    maxTickets: number;
    numDays: number;
    locationId: string;
}

export class EventSectorDTO {
    price: number;
    sectorId: string;
}

export class EventUpdateDTO {
    name: string;
    description: string;
}
