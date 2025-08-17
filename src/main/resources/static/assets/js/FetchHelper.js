const fetchHelper = {
    __setUrl: function (url) {
        if (url.constructor !== URL) {
            // http나 https로 시작하지 않는다면?
            if (!url.startsWith("http")) {
                // url 문자열에 기본 URL을 추가 --> http://localhost:8080/
                const baseUrl = window.location.origin; // 현재 페이지의 기본 URL
                // URL 객체 생성 --> /api/my_calc, http://localhost:8080/
                return new URL(url, baseUrl);
            } else {
                // URL 객체 생성
                return new URL(url);
            }
        }

        return url;
    },
    __request: async function (url, method = "get", params = null, loader = '#loader') {
        console.group("FetchHelper ::: %s", new Date().toLocaleString());

        // 요청 URL이 URL객체가 아닌 경우 객체 생성
        url = this.__setUrl(url);

        // post, put, delete 방식에서 파라미터가 FormData 객체가 아닌 경우 객체 변환
        if (method.toLocaleLowerCase() !== "get" && params) {
            if (params.constructor !== FormData) {
                switch (params.constructor) {
                    case SubmitEvent:
                        params = new FormData(params.currentTarget);
                        break;
                    case HTMLFormElement:
                        params = new FormData(params);
                        break;
                    default:
                        const tmp = structuredClone(params);
                        params = new FormData();

                        for (const t in tmp) {
                            const value = tmp[t];
                            if (value) {
                                params.set(t, value);
                            }
                        }
                        break;
                }
            }
        }

        let result = null;      // Ajax 연동 결과가 저장될 객체
        let options = null;     // post, put, delete에서 사용할 옵션 변수

        if (method.toLocaleLowerCase() !== "get") {
            options = {
                method: method,
                cache: "no-cache",
                headers: {},
                body: params,
            };

            console.log(`options: %o`, options);
        }

        if (typeof loader == 'string') {
            loader = document.querySelector(loader);
        }

        if (loader) {
            loader.style.display = 'block';
        }

        try {
            // 백엔드로부터의 응답 받기
            console.log(`→ [${method}] ${url}`);
            const response = await fetch(url, options);

            // 백엔드가 에러를 보내왔다면?
            if (parseInt(response.status / 100) != 2) {
                let message = response.statusText;
                let json = await response.json();

                //console.log(response);
                //console.log(json);

                if (!message) {
                    message = json?.message ?? "서버에서 에러가 발생했습니다.";
                }

                // 에러 객체 생성후 에러 발생 --> catch로 이동함
                const err = new Error(message);
                err.status = response.status;
                throw err;
            }

            // 응답으로부터 JSON 데이터 추출
            result = await response.json();
        } catch (err) {
            console.error(err);
            console.groupEnd();
            throw err;
        } finally {
            if (loader) {
                loader.style.display = 'none';
            }
        }

        console.log("← %o", result);
        console.groupEnd();

        return result;
    },
    get: async function(urlString, params, loader) {
        url = this.__setUrl(urlString);

        // params를 url에 QueryString형태로 추가해야 함
        if (params) {
            switch (params.constructor) {
                case SubmitEvent:
                    params = new FormData(params.currentTarget);
                    break;
                case HTMLFormElement:
                    params = new FormData(params);
                    break;
            }

            if (params.constructor === FormData) {
                for (const p of params.keys()) {
                    const value = params.get(p);
                    if (value) {
                        url.searchParams.set(p, value);
                    }
                }
            } else {
                for (const p in params) {
                    const value = params[p];
                    if (value) {
                        url.searchParams.set(p, params[p]);
                    }
                }
            }
        }

        return await this.__request(url, "get", null, loader);
    },
    post: async function(urlString, params, loader) {
        // params를 FormData객체 형태로 변환해야 함
        return await this.__request(urlString, "post", params, loader);
    },
    put: async function(urlString, params, loader) {
        // params를 FormData객체 형태로 변환해야 함
        return await this.__request(urlString, "put", params, loader);
    },
    delete: async function(urlString, params, loader) {
        // params를 FormData객체 형태로 변환해야 함
        return await this.__request(urlString, "delete", params, loader);
    }
}