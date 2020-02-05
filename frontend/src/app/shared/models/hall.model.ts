export class HallDTO {
    id: string;
    name: string;
    standingNr: number;
    sittingNr: number;
}
export class Location {
    id: string;
    name: string;
    description: string;
    halls: Hall[];
}
export class Hall {
    id: string;
    name: string;
    sectors: Sector[];
}

export abstract class Sector {
    id: string;
    name: string;
    type: string;
}

export class SittingSector extends Sector {
    numRows: number;
    numCols: number;
}

export class StandingSector extends Sector {
    capacity: number;
}
