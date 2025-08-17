import kioke from '@/app/api';
import type { DashboardViewerType, DashboardWidgetType } from '@/constant/dashboard';
import { MimeType } from '@/constant/mime';

export interface UpdateDashboardRequestBody {
  viewerType: DashboardViewerType;
  widgets: {
    type: DashboardWidgetType;
    x: number;
    y: number;
    content: any;
  }[];
}

function url() {
  return '/dashboards/me';
}

export async function updateDashboard(requestBody: UpdateDashboardRequestBody) {
  return kioke<void>(url(), {
    method: 'PUT',
    body: JSON.stringify(requestBody),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
