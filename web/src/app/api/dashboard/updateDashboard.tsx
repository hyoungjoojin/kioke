import kioke from '@/app/api';
import type { WidgetType } from '@/constant/dashboard';
import { MimeType } from '@/constant/mime';

export interface UpdateDashboardRequest {
  widgets: {
    type: WidgetType;
    x: number;
    y: number;
    content: any;
  }[];
}

function url() {
  return '/dashboards/me';
}

export async function updateDashboard(body: UpdateDashboardRequest) {
  return kioke<void>(url(), {
    method: 'PUT',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
