import { Component } from "@angular/core";
import { Input } from "@angular/core";
import { OnChanges } from "@angular/core/src/metadata/lifecycle_hooks";

@Component({
    selector: 'agobox',
    templateUrl: './agobox.component.html',
    styleUrls: ['./agobox.component.css']
})
export class AgoBoxComponent implements OnChanges {
    @Input() seconds: number;

    text: string;
    ngOnChanges() {
        this.text = this.timeSince(Math.floor(Date.now() / 1000) - this.seconds);
    }

    ngOnInit() {
        this.ngOnChanges();
    }

    timeSince(seconds) {
        var interval = Math.floor(seconds / 31536000);

        if (interval > 1) {
            return interval + " years";
        }
        interval = Math.floor(seconds / 2592000);
        if (interval > 1) {
            return interval + " months";
        }
        interval = Math.floor(seconds / 86400);
        if (interval > 1) {
            return interval + " days";
        }
        interval = Math.floor(seconds / 3600);
        if (interval > 1) {
            return interval + " hours";
        }
        interval = Math.floor(seconds / 60);
        if (interval > 1) {
            return interval + " minutes";
        }
        return Math.floor(seconds) + " seconds";
    }
}